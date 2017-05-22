package org.processmining.plugins.rttmining;

import java.util.Iterator;

import javax.swing.JOptionPane;

import org.deckfour.xes.model.XLog;
import org.processmining.contexts.uitopia.UIPluginContext;
import org.processmining.contexts.uitopia.annotations.UITopiaVariant;
import org.processmining.framework.plugin.annotations.Plugin;

import com.carrotsearch.hppc.ObjectArrayList;

public class RTTmining {
	
	// Memorizza qui le configurazioni generali del plugin
	public static Settings settings;
	// Gestore dei vincoli
	public static ConstraintsManager vincoli;
	
	private static UIPluginContext context;
	private static XLog log;
	
	/*
	 * Queste notazioni specificano le informazioni di contesto
	 * del plugin, come parametri di input e output
	 * 
	 * Da notare che sono associate ad un metodo statico,
	 * che verrà richiamato all'esecuzione del plugin
	 */	
	@Plugin(
        name = "RTTmining Plugin", 
        parameterLabels = {}, 
        returnLabels = { "Hello world string" }, 
        returnTypes = { String.class }, 
        userAccessible = true, 
        help = "Produces the string: 'Hello world'"
    )
    @UITopiaVariant(
        affiliation = "Process Mining with CSP", 
        author = "Riccardi, Tagliente, Tota", 
        email = "??"
    )
	/*
	 * Consiste nel Main del plugin stesso, 
	 * l'esecutore di tutto e il gestore di input ed output
	 */
    public static String Process(UIPluginContext context, XLog log) throws Exception {
		// Rendi il contesto e l'input globale a tutto il plugin
		RTTmining.context = context;
		RTTmining.log = log;		
		
		// determina le impostazioni del plugin
		SettingsView settingsView = new SettingsView(context, log);
		settings = settingsView.show();
		
		System.out.println("\n\nRTTmining\n\nSettings:\n");
	    System.out.println("sigma log noise " + settings.sigmaLogNoise);
	    System.out.println("delta fall factor  " + settings.fallFactor);
	    System.out.println("relative to best  " + settings.relativeToBest);
	    
	    // Inizializzo le variabili utili all'algoritmo
	    // 1. Il gestore dei vincoli
	    vincoli = new ConstraintsManager();
	    // 2. Inizializza l'oggetto che si occupa di gestire
	    // l'algoritmo di cnmining
	    CNMining cnmining = new CNMining();
	    
	    // esegui il parsing dei vincoli, se richiesto
		caricaVincoli();
		
		// Prepara l'ambiente di lavoro		
		LogUnfolder.aggiungiAttivitaFittizia(log);
		// rimozione dei cicli
	    LogUnfolderResult unfoldResult = LogUnfolder.unfold(log);
		// l'operazione di unfold mi produce 4 output
	    cnmining.creaVincoliUnfolded(vincoli, unfoldResult);
	    
	    /*
	    if (settings.areConstraintsAvailable()) {
	      cnm.creaVincoliUnfolded(vincoli_positivi, vincoli_negati, lista_forbidden, vincoli_positivi_unfolded, 
	        vincoli_negati_unfolded, lista_forbidden_unfolded, map);
	    }
	    */
	    
		///
		
		return "Hello RTTMining";
	}
	
	/*
	 * Esegui il parsing dei vincoli da file xml
	 * e caricali sulle variabili locali
	 */
	private static void caricaVincoli(){
		// Se ho dato il consenso al caricamento dei vincoli
		if( settings.areConstraintsAvailable() )
		{
			// procedo a eseguire il parsing
			// essendo questi in formato xml
			ConstraintParser constraintsParser = new ConstraintParser(settings.constraintsFilename);
			if(constraintsParser.parse()){
				 ObjectArrayList<Constraint> constraints = constraintsParser.getConstraints();
		         if (constraints.size() == 0) {
		        	 JOptionPane.showMessageDialog(null, "No constraints contained in the input file...");
		         }
		         for (int i = 0; i < constraints.size(); i++)
		         {
		        	 Constraint vincolo = constraints.get(i);
		        	 if (vincolo.isPositiveConstraint())
		        		 vincoli.positivi.add(vincolo);
		        	 else
		        	 {
		        		 vincoli.negati.add(vincolo);
		        		 
		                 // TODO: riga 955 CNMining, controllare bene
		        		 
		        		 // Questa implementazione è di mia interpretazione
		        		 Iterator<String> headIterator, bodyIterator;
		        		 headIterator = vincolo.getHeadList().iterator();
		        		 bodyIterator = vincolo.getBodyList().iterator();
		        		 
		        		 // trova la testa e la coda
		        		 while(headIterator.hasNext())
		        			 headIterator.next();
		        		 while(bodyIterator.hasNext())
		        			 bodyIterator.next();
		        		 
		        		 vincoli.forbidden.add(new Forbidden(bodyIterator.toString(), headIterator.toString()));		        		 
		        	 }		        		 		     
		         }
			}
			else {
				// procedi come se non fossero stati specificati vincoli
				JOptionPane.showMessageDialog(null, "Invalid constraints file\nThe algoritm will now run without constraints...");		          
			}			
		}
	}
	
}

package edu.iut.exceptions;

import edu.iut.app.ApplicationSession;

// Exercice 2
public class IUTException extends Exception{
	public IUTException() {
		super();
		ApplicationSession.instance().getExceptionLogger().severe("Exception (aucun message spécifié)");
		// Logger une erreur avec le message empty en utilisant le singleton session, ie le logger défini dans la session */

	}
	public IUTException(IUTException e) {
		super (e);
		ApplicationSession.instance().getExceptionLogger().severe(e.getMessage());
		// Logger une erreur avec le message contenu dans 'e'  en utilisant le singleton session, ie le logger défini dans la session */
	}
	public IUTException(String message) {
		super(message);
		ApplicationSession.instance().getExceptionLogger().severe(message);
		// Logger une erreur avec le message contenu dans 'message'  en utilisant le singleton session, ie le logger défini dans la session */
	}
	
}

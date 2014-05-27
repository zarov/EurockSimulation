package fr.utbm.gi.vi51.g3.framework.environment;

public enum Goal {
	
	EAT(0), DRINK(0), PEE(0), VOMIT(0), SEE_GIG(0), GET_REST(0);
	
	public int insistence;
	
	Goal(int insistence){
		this.insistence = insistence;
	}
	public int getNeedInsistence(){
		return this.insistence;
		
	}

}

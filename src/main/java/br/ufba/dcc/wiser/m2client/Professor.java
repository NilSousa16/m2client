package br.ufba.dcc.wiser.m2client;

public class Professor extends Funcionario{
	
	public void ensinarDisciplina(String disc) {
		System.out.println(getNome() + " está ensinando " + disc);
	}
	
}



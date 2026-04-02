package com.br.ejb.entity;

import jakarta.annotation.Generated;
import jakarta.persistence.metamodel.EntityType;
import jakarta.persistence.metamodel.SingularAttribute;
import jakarta.persistence.metamodel.StaticMetamodel;
import java.math.BigDecimal;

/**
 * Static metamodel for {@link com.br.ejb.entity.Beneficio}
 **/
@StaticMetamodel(Beneficio.class)
@Generated("org.hibernate.processor.HibernateProcessor")
public abstract class Beneficio_ {

	
	/**
	 * @see #id
	 **/
	public static final String ID = "id";
	
	/**
	 * @see #nome
	 **/
	public static final String NOME = "nome";
	
	/**
	 * @see #descricao
	 **/
	public static final String DESCRICAO = "descricao";
	
	/**
	 * @see #valor
	 **/
	public static final String VALOR = "valor";
	
	/**
	 * @see #ativo
	 **/
	public static final String ATIVO = "ativo";
	
	/**
	 * @see #version
	 **/
	public static final String VERSION = "version";

	
	/**
	 * Static metamodel type for {@link com.br.ejb.entity.Beneficio}
	 **/
	public static volatile EntityType<Beneficio> class_;
	
	/**
	 * Static metamodel for attribute {@link com.br.ejb.entity.Beneficio#id}
	 **/
	public static volatile SingularAttribute<Beneficio, Long> id;
	
	/**
	 * Static metamodel for attribute {@link com.br.ejb.entity.Beneficio#nome}
	 **/
	public static volatile SingularAttribute<Beneficio, String> nome;
	
	/**
	 * Static metamodel for attribute {@link com.br.ejb.entity.Beneficio#descricao}
	 **/
	public static volatile SingularAttribute<Beneficio, String> descricao;
	
	/**
	 * Static metamodel for attribute {@link com.br.ejb.entity.Beneficio#valor}
	 **/
	public static volatile SingularAttribute<Beneficio, BigDecimal> valor;
	
	/**
	 * Static metamodel for attribute {@link com.br.ejb.entity.Beneficio#ativo}
	 **/
	public static volatile SingularAttribute<Beneficio, Boolean> ativo;
	
	/**
	 * Static metamodel for attribute {@link com.br.ejb.entity.Beneficio#version}
	 **/
	public static volatile SingularAttribute<Beneficio, Long> version;

}


/* 
 * $Id$
 * 
 * Copyright (c) 2007-13 Stephane GALLAND.
 * 
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * 
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * 
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 * This program is free software; you can redistribute it and/or modify
 */
package fr.utbm.gi.vi51.g3.framework.environment;

import javax.vecmath.Vector2d;

/**
 * Abstract implementation of a motion influence.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class MotionInfluence extends Influence<AbstractSituatedObject> {

	private final DynamicType type;
	private final Vector2d linearInfluence = new Vector2d();
	private double angularInfluence = 0.;
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, AbstractSituatedObject influencedObject, Vector2d linearInfluence, double angularInfluence) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluence);
		setAngularInfluence(angularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, AbstractSituatedObject influencedObject, double linearInfluenceX, double linearInfluenceY, double angularInfluence) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY);
		setAngularInfluence(angularInfluence);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, AbstractSituatedObject influencedObject, Vector2d linearInfluence) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, AbstractSituatedObject influencedObject, double linearInfluenceX, double linearInfluenceY) {
		super(influencedObject);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, AbstractSituatedObject influencedObject, double angularInfluence) {
		super(influencedObject);
		this.type = type;
		setAngularInfluence(angularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param influencedObject is the influenced object.
	 */
	protected MotionInfluence(DynamicType type, AbstractSituatedObject influencedObject) {
		super(influencedObject);
		this.type = type;
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, Vector2d linearInfluence, double angularInfluence) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluence);
		setAngularInfluence(angularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, double linearInfluenceX, double linearInfluenceY, double angularInfluence) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY);
		setAngularInfluence(angularInfluence);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, Vector2d linearInfluence) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param linearInfluenceX is the linear influence to apply on the object.
	 * @param linearInfluenceY is the linear influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, double linearInfluenceX, double linearInfluenceY) {
		super(null);
		this.type = type;
		setLinarInfluence(linearInfluenceX, linearInfluenceY);
	}

	/**
	 * @param type indicates if the influence is kinematic or steering.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	protected MotionInfluence(DynamicType type, double angularInfluence) {
		super(null);
		this.type = type;
		setAngularInfluence(angularInfluence);
	}
	
	/**
	 * @param type indicates if the influence is kinematic or steering.
	 */
	protected MotionInfluence(DynamicType type) {
		super(null);
		this.type = type;
	}

	/** Set the linear influence.
	 * 
	 * @param l is the linear influence
	 */
	public void setLinarInfluence(Vector2d l) {
		assert(l!=null);
		this.linearInfluence.set(l);
	}
		
	/** Set the linear influence.
	 * 
	 * @param dx is the linear influence
	 * @param dy is the linear influence
	 */
	public void setLinarInfluence(double dx, double dy) {
		this.linearInfluence.set(dx, dy);
	}

	/** Set the angular influence.
	 * 
	 * @param a
	 */
	public void setAngularInfluence(double a) {
		this.angularInfluence = a;
	}

	/** Replies the linear influence.
	 * 
	 * @return the linear influence
	 */
	public Vector2d getLinearInfluence() {
		return this.linearInfluence;
	}
		
	/** Replies the angular influence.
	 * 
	 * @return the angular influence
	 */
	public double getAngularInfluence() {
		return this.angularInfluence;
	}
	
	/** Replies the type of the influence.
	 * 
	 * @return the type of the influence.
	 */
	public DynamicType getType() {
		return this.type;
	}

}
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

import java.util.Collections;
import java.util.List;

import javax.vecmath.Vector2d;

import org.arakhne.afc.vmutil.locale.Locale;
import org.janusproject.kernel.address.AgentAddress;

import fr.utbm.gi.vi51.g3.framework.util.GeometryUtil;

/**
 * Object on the environment.
 * 
 * @author St&eacute;phane GALLAND &lt;stephane.galland@utbm.fr&gt;
 * @version $Name$ $Revision$ $Date$
 */
public class AgentBody extends AbstractMobileObject implements Body {

	private final long perceptionRange;
	private final AgentAddress owner;

	private MotionInfluence motionInfluence = null;
	private List<Perception> perceptions = Collections.emptyList();

	/**
	 * @param owner
	 * @param size of the object.
	 * @param maxLinearSpeed is the maximal linear speed.
	 * @param maxLinearAcceleration is the maximal linear acceleration.
	 * @param maxAngularSpeed is the maximal angular speed.
	 * @param maxAngularAcceleration is the maximal angular acceleration.
	 */
	public AgentBody(AgentAddress owner, double size, double maxLinearSpeed, double maxLinearAcceleration, double maxAngularSpeed, double maxAngularAcceleration, long perceptionRange) {
		super(size, maxLinearSpeed, maxLinearAcceleration, maxAngularSpeed, maxAngularAcceleration);
		this.owner = owner;
		this.perceptionRange = perceptionRange;
	}

	/** {@inheritDoc}
	 */
	@Override
	public String toString() {
		return Locale.getString(AbstractSituatedObject.class, "BODY_OF", this.owner); //$NON-NLS-1$
	}


	/** Replies the owner of this body.
	 * 
	 * @return the owner of this body.
	 */
	@Override
	public AgentAddress getOwner() {
		return this.owner;
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	@Override
	public void influenceKinematic(Vector2d linearInfluence, double angularInfluence) {
		Vector2d li;
		if (linearInfluence!=null) {
			li = new Vector2d(linearInfluence);
			double nSpeed = li.length();
			if (nSpeed>getMaxLinearSpeed()) {
				li.normalize();
				li.scale(getMaxLinearSpeed());
			}
		}
		else {
			li = new Vector2d();
		}
		double ai = GeometryUtil.clamp(angularInfluence, -getMaxAngularSpeed(), getMaxAngularSpeed());
		this.motionInfluence = new MotionInfluence(DynamicType.KINEMATIC, this, li, ai);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	@Override
	public void influenceSteering(Vector2d linearInfluence, double angularInfluence) {
		Vector2d li;
		if (linearInfluence!=null) {
			li = new Vector2d(linearInfluence);
			double nSpeed = li.length();
			if (nSpeed>getMaxLinearAcceleration()) {
				li.normalize();
				li.scale(getMaxLinearAcceleration());
			}
		}
		else {
			li = new Vector2d();
		}
		double ai = GeometryUtil.clamp(angularInfluence, -getMaxAngularAcceleration(), getMaxAngularAcceleration());
		this.motionInfluence = new MotionInfluence(DynamicType.STEEERING, this, li, ai);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	@Override
	public void influenceKinematic(Vector2d linearInfluence) {
		influenceKinematic(linearInfluence, 0.);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param linearInfluence is the linear influence to apply on the object.
	 */
	@Override
	public void influenceSteering(Vector2d linearInfluence) {
		influenceSteering(linearInfluence, 0.);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	@Override
	public void influenceKinematic(double angularInfluence) {
		influenceKinematic(null, angularInfluence);
	}

	/** Invoked to send the influence to the environment.
	 * 
	 * @param angularInfluence is the angular influence to apply on the object.
	 */
	@Override
	public void influenceSteering(double angularInfluence) {
		influenceSteering(null, angularInfluence);
	}

	/** Replies all the perceived objects.
	 * 
	 * @return the perceived objects.
	 */
	@Override
	public List<Perception> getPerceivedObjects() {
		return Collections.unmodifiableList(this.perceptions);
	}

	/** Replies the influence.
	 * 
	 * @return the influence.
	 */
	MotionInfluence consumeInfluence() {
		MotionInfluence i = this.motionInfluence;
		this.motionInfluence = null;
		if (i!=null) i.setEmitter(getOwner());
		return i;
	}

	/** Set the perceptions.
	 * 
	 * @param perceptions
	 */
	void setPerceptions(List<Perception> perceptions) {
		assert(perceptions!=null);
		this.perceptions = perceptions;
	}

	public double getPerceptionRange() {
		return this.perceptionRange;
	}
	public double getVelocity(){
		return 0;
	}

	@Override
	public boolean isAttendant() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isOK() {
		// TODO Auto-generated method stub
		return false;
	}
}
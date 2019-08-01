package cropcert.user.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name="farmer")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "farmer")
public class Farmer extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	@Column( name = "farm_code", nullable = false)
	private String farmCode;
	@Column( name = "farm_number", nullable = false)
	private int farmNumber;
	@Column (name = "cc_code")
	private int ccCode;

	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public String getFarmCode() {
		return farmCode;
	}
	public void setFarmCode(String farmCode) {
		this.farmCode = farmCode;
	}

	public int getFarmNumber() {
		return farmNumber;
	}
	public void setFarmNumber(int farmNumber) {
		this.farmNumber = farmNumber;
	}

	public int getCcCode() {
		return ccCode;
	}
	public void setCcCode(int ccCode) {
		this.ccCode = ccCode;
	}

}

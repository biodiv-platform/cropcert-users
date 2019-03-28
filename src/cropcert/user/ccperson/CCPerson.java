package cropcert.user.ccperson;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import cropcert.user.user.User;

@Entity
@Table(name="cc_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "manager")
public class CCPerson extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	
	@Column (name = "cc_code")
	private int ccCode;
	

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public int getCcCode() {
		return ccCode;
	}

	public void setCcCode(int ccCode) {
		this.ccCode = ccCode;
	}

}

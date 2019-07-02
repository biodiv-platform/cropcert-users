package cropcert.user.coperson;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import cropcert.user.user.User;

@Entity
@Table(name="co_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "co")
public class COPerson extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	
	@Column (name = "co_code")
	private int coCode;
	

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	
	public int getCoCode() {
		return coCode;
	}
	
	public void setCoCode(int factoryCode) {
		this.coCode = factoryCode;
	}

}

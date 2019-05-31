package cropcert.user.factoryperson;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import cropcert.user.user.User;

@Entity
@Table(name="factory_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "factory")
public class FactoryPerson extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	
	@Column (name = "factory_code")
	private int factoryCode;
	

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}
	
	public int getFactoryCode() {
		return factoryCode;
	}
	
	public void setFactoryCode(int factoryCode) {
		this.factoryCode = factoryCode;
	}

}

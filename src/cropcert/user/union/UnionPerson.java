package cropcert.user.union;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import cropcert.user.user.User;

@Entity
@Table(name="union_person")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "union")
public class UnionPerson extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	
	@Column (name = "union_name")
	private int unionName;
	

	public String getMembershipId() {
		return membershipId;
	}

	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public int getUnionName() {
		return unionName;
	}

	public void setUnionName(int unionName) {
		this.unionName = unionName;
	}
	
}

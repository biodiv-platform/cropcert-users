package cropcert.user.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name="farmer")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "farmer")
@ApiModel("Farmer")
public class Farmer extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;
	
	@Column(name = "membership_id", nullable=false)
	private String membershipId;
	@Column (name = "num_coffee_plots")
	private Integer numCoffeePlots;
	@Column (name = "num_coffee_Trees")
	private Integer numCoffeeTrees;
	@Column (name = "farm_area")
	private Float farmArea;
	@Column (name = "coffee_area")
	private Float coffeeArea;
	@Column (name = "is_ft")
	private Boolean isFT;
	@Column (name = "is_org")
	private Boolean isOrg;
	@Column (name = "cc_code", nullable = false)
	private Long ccCode;
	@Column (name = "per_coop_id")
	private Long perCoopId;

	public String getMembershipId() {
		return membershipId;
	}
	public void setMembershipId(String membershipId) {
		this.membershipId = membershipId;
	}

	public Integer getNumCoffeePlots() {
		return numCoffeePlots;
	}
	public void setNumCoffeePlots(Integer numCoffeePlots) {
		this.numCoffeePlots = numCoffeePlots;
	}

	public Integer getNumCoffeeTrees() {
		return numCoffeeTrees;
	}
	public void setNumCoffeeTrees(Integer numCoffeeTrees) {
		this.numCoffeeTrees = numCoffeeTrees;
	}

	public Float getFarmArea() {
		return farmArea;
	}
	public void setFarmArea(Float farmArea) {
		this.farmArea = farmArea;
	}

	public Float getCoffeeArea() {
		return coffeeArea;
	}
	public void setCoffeeArea(Float coffeeArea) {
		this.coffeeArea = coffeeArea;
	}

	public Boolean getIsFT() {
		return isFT;
	}
	public void setIsFT(Boolean isFT) {
		this.isFT = isFT;
	}

	public Boolean getIsOrg() {
		return isOrg;
	}
	public void setIsOrg(Boolean isOrg) {
		this.isOrg = isOrg;
	}

	public Long getCcCode() {
		return ccCode;
	}
	public void setCcCode(Long ccCode) {
		this.ccCode = ccCode;
	}
	
	public Long getPerCoopId() {
		return perCoopId;
	}
	public void setPerCoopId(Long perCoopId) {
		this.perCoopId = perCoopId;
	}
}

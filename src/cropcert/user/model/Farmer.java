package cropcert.user.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table(name = "farmer")
@XmlRootElement
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue(value = "farmer")
@ApiModel("Farmer")
public class Farmer extends User {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5072383312664930067L;

	@Column(name = "membership_id", nullable = false)
	private String membershipId;
	@Column(name = "num_coffee_plots")
	private Integer numCoffeePlots;
	@Column(name = "num_coffee_Trees")
	private Integer numCoffeeTrees;
	@Column(name = "farm_area")
	private Float farmArea;
	@Column(name = "coffee_area")
	private Float coffeeArea;

	@Column(name = "farmer_code")
	private String farmerCode;
	@Column(name = "cc_code", nullable = false)
	private Long ccCode;
	@Column(name = "field_coordinator")
	private Long fieldCoOrdinator;

	public Farmer() {
		super();
	}

	public Farmer(String membershipId, Integer numCoffeePlots, Integer numCoffeeTrees, Float farmArea, Float coffeeArea,
			String farmerCode, Long ccCode, Long fieldCoOrdinator) {
		super();
		this.membershipId = membershipId;
		this.numCoffeePlots = numCoffeePlots;
		this.numCoffeeTrees = numCoffeeTrees;
		this.farmArea = farmArea;
		this.coffeeArea = coffeeArea;
		this.farmerCode = farmerCode;
		this.ccCode = ccCode;
		this.fieldCoOrdinator = fieldCoOrdinator;
	}

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

	public String getFarmerCode() {
		return farmerCode;
	}

	public void setFarmerCode(String farmerCode) {
		this.farmerCode = farmerCode;
	}

	public Long getCcCode() {
		return ccCode;
	}

	public void setCcCode(Long ccCode) {
		this.ccCode = ccCode;
	}

	public Long getFieldCoOrdinator() {
		return fieldCoOrdinator;
	}

	public void setFieldCoOrdinator(Long fieldCoOrdinator) {
		this.fieldCoOrdinator = fieldCoOrdinator;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}
}

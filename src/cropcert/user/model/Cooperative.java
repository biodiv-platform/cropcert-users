package cropcert.user.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table (name = "cooperative")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "co")
@ApiModel("Cooperative")
public class Cooperative extends CropcertEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1216212900838354949L;

	@Column (name = "union_code")
	private Long unionCode;
	
	@Column(name = "num_farmer")
	private Long numFarmer;
	
	@Column(name = "farmer_seq_number")
	private Long farSeqNumber;
	
	public Long getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}
	
	public Long getNumFarmer() {
		return numFarmer;
	}
	public void setNumFarmer(Long numFarmer) {
		this.numFarmer = numFarmer;
	}
	
	public Long getFarSeqNumber() {
		return farSeqNumber;
	}
	public void setFarSeqNumber(Long farSeqNumber) {
		this.farSeqNumber = farSeqNumber;
	}
}

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
	
	public Long getUnionCode() {
		return unionCode;
	}
	public void setUnionCode(Long unionCode) {
		this.unionCode = unionCode;
	}
}

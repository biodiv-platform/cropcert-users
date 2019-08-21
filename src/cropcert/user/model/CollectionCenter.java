package cropcert.user.model;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import io.swagger.annotations.ApiModel;

@Entity
@Table (name = "collection_center")
@XmlRootElement
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "cc")
@ApiModel("CollectionCenter")
public class CollectionCenter extends CropcertEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 109808509127731770L;

	@Column (name = "type")
	private String type;
	@Column ( name = "co_operative_code")
	private Long cooperativeCode;

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public Long getCooperativeCode() {
		return cooperativeCode;
	}
	public void setCooperativeCode(Long cooperativeCode) {
		this.cooperativeCode = cooperativeCode;
	}
	
}

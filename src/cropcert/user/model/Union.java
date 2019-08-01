package cropcert.user.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table (name = "union_table")
@PrimaryKeyJoinColumn(name="id")
@DiscriminatorValue(value= "union")
public class Union extends CropcertEntity{

	/**
	 * 
	 */
	private static final long serialVersionUID = 109808509127731770L;

}

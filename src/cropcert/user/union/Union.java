package cropcert.user.union;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@XmlRootElement
@Table (name = "union_table")
public class Union implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 109808509127731770L;

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "union_id_generator")
	@SequenceGenerator(name = "union_id_generator", sequenceName = "union_id_seq", allocationSize = 50)
	@Column(name = "id", updatable = false, nullable = false)
	private Long unionId;
	
	@Column (name = "union_code", nullable=false)
	private int unionCode;
	
	@Column (name = "union_name", nullable=false)
	private String unionName;
	
	@Column (name = "village")
	private String village;
	
	@Column (name = "sub_country")
	private String subCountry;
	
	@Column (name = "latitude")
	private float latitude;
	
	@Column( name = "longitude")
	private float longitude;
	
	@Column( name = "altitude")
	private float altitude;

	public Long getUnionId() {
		return unionId;
	}

	public void setUnionId(Long unionId) {
		this.unionId = unionId;
	}

	public int getUnionCode() {
		return unionCode;
	}

	public void setUnionCode(int unionCode) {
		this.unionCode = unionCode;
	}

	public String getUnionName() {
		return unionName;
	}

	public void setUnionName(String unionName) {
		this.unionName = unionName;
	}

	public String getVillage() {
		return village;
	}

	public void setVillage(String village) {
		this.village = village;
	}

	public String getSubCountry() {
		return subCountry;
	}

	public void setSubCountry(String subCountry) {
		this.subCountry = subCountry;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public float getAltitude() {
		return altitude;
	}

	public void setAltitude(float altitude) {
		this.altitude = altitude;
	}	
}

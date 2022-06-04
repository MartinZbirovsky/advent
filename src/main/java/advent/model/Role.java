package advent.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
public class Role {
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(nullable = false, length = 50, unique = true)
	private String name;
	public Role(String name) {
		this.name = name;
	}
	public Role(Integer id) {
		super();
		this.id = id;
	}

	@Override
	public String toString() {
		return this.name;
	}
}

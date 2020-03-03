package swimming.competition.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.w3c.dom.NameList;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Participant {
	private int Id;
	private String name;
	private int age;
	private List<Proba> probe;
	
	public Participant(String name, int age){
		this.name = name;
		this.age = age;
	}
	public void addProba(Proba p){
		if(probe == null){
			probe = new ArrayList<>();
			probe.add(p);
		}else{
			probe.add(p);
		}
	}
}

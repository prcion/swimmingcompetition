package swimming.competition.domain;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
public class Proba {
	private int Id;
	private String distance;
	private String style;
	private int idParticipant;
	
	public Proba(String distance, String style){
		this.distance = distance;
		this.style = style;
	}
}

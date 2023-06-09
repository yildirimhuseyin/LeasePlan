package leaseplan.pojo;

import leaseplan.utilities.APIUtils;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;
@Getter
@Setter //These comes from lombok library and autogenerated methods
@ToString
public class Pastas {

    public Pastas() {
        this.allPastas = APIUtils.getAllPasta();
    }

    private List<Pasta> allPastas;




}

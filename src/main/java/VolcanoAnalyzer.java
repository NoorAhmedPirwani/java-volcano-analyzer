import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class VolcanoAnalyzer {
    private List<Volcano> volcanos;

    public void loadVolcanoes(Optional<String> pathOpt) throws IOException, URISyntaxException {
        try{
            String path = pathOpt.orElse("volcano.json");
            URL url = this.getClass().getClassLoader().getResource(path);
            String jsonString = new String(Files.readAllBytes(Paths.get(url.toURI())));
            ObjectMapper objectMapper = new ObjectMapper();
            TypeFactory typeFactory = objectMapper.getTypeFactory();
            volcanos = objectMapper.readValue(jsonString, typeFactory.constructCollectionType(List.class, Volcano.class));
        } catch(Exception e){
            throw(e);
        }
    }

    public Integer numbVolcanoes(){

        return volcanos.size();
    }

    public List<Volcano> eruptedInEighties(){
        return volcanos.stream().filter(eighties -> eighties.getYear() >= 1980 && eighties.getYear() < 1990).collect(Collectors.toList());
    }

    public List<String> highVEI(){
        return volcanos.stream().filter(high -> high.getVEI() >= 6).map(high->high.getName()).collect(Collectors.toList());
    }

    // public List<Volcano> mostDeadly() {

    //     return volcanos.stream().sorted(Comparator.comparing((Volcano()::getDEATHS))).limit(1).collect(Collectors.toList());

    // }

    


    
    
    
    
    //add methods here to meet the requirements in README.md

}

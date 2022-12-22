import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;
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

    //01. Return the volcanoes that erupted in the 1980s.
    public List<Volcano> eruptedInEighties(){
        return volcanos.stream().filter(eighties -> eighties.getYear() >= 1980 && eighties.getYear() < 1990).collect(Collectors.toList());
    }

    //02. Return an array of the names of volcanoes that had an eruption with a Volcanic Explosivity Index (VEI) of 6 or higher.
    public List<String> highVEI(){
        return volcanos.stream().filter(high -> high.getVEI() >= 6).map(high->high.getName()).collect(Collectors.toList());
    }

    //03. Return the eruption with the highest number of recorded deaths.
    // public List<Volcano> mostDeadly() {
    //     return volcanos.stream().sorted(Comparator.comparing((Volcano::getDEATHS))).limit(1).collect(Collectors.toList());

    //04. Return the percentage of eruptions that caused tsunamis.
    public double causedTsunami() {
        return volcanos.stream().filter(tsuPercentage -> tsuPercentage.getTsu().equals("tsu")).count() * 100 / volcanos.size();
    }

    //05. Return the most common type of volcano in the set.
    public String mostCommonType() {       
        return volcanos.stream().collect(Collectors.collectingAndThen(Collectors.groupingBy(Volcano::getType, Collectors.counting())
        ,counted -> counted.entrySet().stream().max(Map.Entry.comparingByValue()).get().getKey()));
     }

    //06. Return the number of eruptions when supplied a country as an argument.
    public int eruptionsByCountry(String country) {
        return volcanos.stream().filter(i -> i.getCountry().equals(country)).collect(Collectors.toList()).size();
    }

    //07. Return the average elevation of all eruptions.
    public double averageElevation(){
        return volcanos.stream().mapToDouble(Volcano::getElevation).sum() / volcanos.size();
    }
    
    //add methods here to meet the requirements in README.md

}

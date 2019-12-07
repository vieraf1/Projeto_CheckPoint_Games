package checkpoint;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;



public class CheckPoint {

    public static void main(String[] args) {
        
        Date data1 = new Date("01/12/1999");
        Date data2 = new Date("30/11/2019");
        
        LocalDateTime dt1 = data1.toInstant().atZone( ZoneId.systemDefault() ).toLocalDateTime();
        LocalDateTime dt2 = LocalDateTime.now();
        
        int ano = (int) dt1.until(dt2, ChronoUnit.YEARS);

        System.out.println("anos: " + (int) ano / 12);

    }

}

package vue;

import javax.persistence.NoResultException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by zuoduzuodu on 17/03/2017.
 */
public class Test
{
    public static void main(String[] args) throws Exception {
    try{
        throw new NoResultException();
    }
    catch (NoResultException e){
        System.out.println("ok");

    }
    catch (Exception e)
    {
        System.out.println("haha");
    }
    }
}
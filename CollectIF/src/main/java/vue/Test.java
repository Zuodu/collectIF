package vue;

import dao.JpaUtil;
import metier.modele.Activite;
import metier.service.ServiceMetier;

/**
 * Created by zuoduzuodu on 17/03/2017.
 */
public class Test
{
    public static void main(String[] args) throws Exception {

        JpaUtil.init();
        ServiceMetier service = new ServiceMetier();

    }
}
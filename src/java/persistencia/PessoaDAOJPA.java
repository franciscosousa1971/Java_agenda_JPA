

package persistencia;
import modelo.Pessoa;
import dao.PessoaDAO;
import javax.persistence.EntityManager;


public class PessoaDAOJPA extends DAOJPA<Pessoa, Integer> implements PessoaDAO{
    public PessoaDAOJPA(EntityManager manager) {
    }  
}

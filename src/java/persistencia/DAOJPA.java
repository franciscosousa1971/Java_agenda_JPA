package persistencia;

import dao.DAO;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.swing.JOptionPane;

public abstract class DAOJPA<T, I> implements DAO<T, I> {

    private JPAUtil conexao;

    @Override
    public T save(T entity) {
        T saved = null;
        try {
            getEntityManager().getTransaction().begin();
            saved = getEntityManager().merge(entity);
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (getEntityManager().getTransaction().isActive() == false) {
                getEntityManager().getTransaction().begin();
            }
            getEntityManager().getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Erro ao salvar elemento na base de dados" + e.getMessage());
        }
        return saved;
    }

    @Override
    public boolean remove(Class<T> classe, I pk) {
        boolean estado = false;
        try {
            getEntityManager().getTransaction().begin();
            getEntityManager().remove(getEntityManager().getReference(classe, pk));
            getEntityManager().getTransaction().commit();
        } catch (Exception e) {
            if (getEntityManager().getTransaction().isActive() == false) {
                getEntityManager().getTransaction().begin();
            }
            getEntityManager().getTransaction().rollback();
            JOptionPane.showMessageDialog(null, "Erro ao remover elemento na base de dados" + e.getMessage());
        }
        return estado;
    }

    @Override
    public T getById(Class<T> classe, I pk) {
        try {
            return getEntityManager().find(classe, pk);

        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public List<T> getAll(Class<T> classe) {
        return getEntityManager().createQuery("select x from " + classe.getSimpleName() + " x").getResultList();
    }

    @Override
    public EntityManager getEntityManager() {
        if (conexao == null) {
            conexao = new JPAUtil();
        }
        return conexao.getEntityManager();
    }

}

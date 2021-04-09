/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import modelo.Pessoa;
import persistencia.PessoaDAOJPA;
import dao.PessoaDAO;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.servlet.http.HttpServletRequest;

@ManagedBean
public class PessoaBean {
   private Pessoa pessoa;
    private List<Pessoa> listaPessoas;

    public PessoaBean() {
        pessoa = new Pessoa();
    }

    public String insere() {
        EntityManager manager = this.getManager();
        PessoaDAO dao = new PessoaDAOJPA(manager);
        dao.save(pessoa);
        this.pessoa = new Pessoa();
        this.listaPessoas = null;
        return "/index.xhtml";
    }

    public String preparaAlteracao() {
        EntityManager manager = this.getManager();
        PessoaDAO dao = new PessoaDAOJPA(manager);
        this.pessoa = dao.getById(Pessoa.class, pessoa.getId());
        return "/pessoa.xhtml";
    }

    public void remove() {
        EntityManager manager = this.getManager();
        PessoaDAO dao = new PessoaDAOJPA(manager);
        dao.remove(Pessoa.class, pessoa.getId());
        this.listaPessoas = null;
    }

    private EntityManager getManager() {
        FacesContext fc = FacesContext.getCurrentInstance();
        ExternalContext ec = fc.getExternalContext();
        HttpServletRequest request = (HttpServletRequest) ec.getRequest();
        return (EntityManager) request.getAttribute("EntityManager");
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }

    public List<Pessoa> getListaPessoas() {
        if (this.listaPessoas == null) {
            EntityManager manager = this.getManager();
            PessoaDAO dao = new PessoaDAOJPA(manager);
            this.listaPessoas = dao.getAll(Pessoa.class);
        }
        return listaPessoas;
    }
 
}

package br.edu.ifg.sistemacomercial.bean;

import br.edu.ifg.sistemacomercial.dao.CategoriaDAO;
import br.edu.ifg.sistemacomercial.dao.ProdutoDAO;
import br.edu.ifg.sistemacomercial.entity.Categoria;
import br.edu.ifg.sistemacomercial.entity.Produto;
import br.edu.ifg.sistemacomercial.util.JsfUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean
@SessionScoped
public class ProdutoBean extends JsfUtil{
    
    private Produto produto;
    private List<Produto> produtos;
    private List <Categoria> categorias;
    private Status statusTela;
    
    private ProdutoDAO produtoDAO;
    
    private enum Status {
        INSERINDO,
        EDITANDO,
        PESQUISANDO
    }
    
    
    @PostConstruct
    public void init(){
        produto = new Produto();
        produtos = new ArrayList<>();   
        statusTela = Status.PESQUISANDO;
        produtoDAO = new ProdutoDAO();
    }
    
    public void novo() throws SQLException{
        statusTela = Status.INSERINDO;
        produto = new Produto();
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        categorias=categoriaDAO.listar();
    }

    public void adicionarProduto(){
        try {
            produtoDAO.salvar(produto);
            produto = new Produto();
            addMensagem("Produto Salvo com sucesso!");
            pesquisar();
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
    public void remover(Produto produto){
        try {
            produtoDAO.deletar(produto);
            produtos.remove(produto);
            addMensagem("Produto Deletado com sucesso!");
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    public void editar(Produto produto){
        
        this.produto = produto;
        statusTela = Status.EDITANDO;
        
        
        CategoriaDAO categoriaDAO = new CategoriaDAO();
        try {
            categorias=categoriaDAO.listar();
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoBean.class.getName()).log(Level.SEVERE, null, ex);
        }
        
       
    }
    
    public void pesquisar(){
        try {
            if(!statusTela.equals(Status.PESQUISANDO)){
                statusTela = Status.PESQUISANDO;
                return;
            }
            produtos = produtoDAO.listar();
            if(produtos == null || produtos.isEmpty()){
                addMensagemAviso("Nenhum produto cadastrado.");
            }
        } catch (SQLException ex) {
            addMensagemErro(ex.getMessage());
        }
    }
    
    public Produto getProduto() {
        return produto;
    }

    public void setUsuario(Produto produto) {
        this.produto = produto;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public String getStatusTela() {
        return statusTela.name();
    }
    
    

    public List<Categoria> getCategorias() {
        return categorias;
    }
     
      public List<Categoria> setCategorias() {
        return categorias;
    }
    
}

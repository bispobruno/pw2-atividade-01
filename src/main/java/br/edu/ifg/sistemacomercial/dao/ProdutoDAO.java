package br.edu.ifg.sistemacomercial.dao;

import br.edu.ifg.sistemacomercial.util.FabricadeConexao;
import br.edu.ifg.sistemacomercial.entity.Categoria;
import br.edu.ifg.sistemacomercial.entity.Produto;
import java.io.Serializable;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO implements Serializable {

    public void salvar(Produto entity) throws SQLException {

        String sqlInsert = "insert into produto (nome, marca, codigo_barras, unidade_medida, categoria_id,id"
                + ") values (?, ?, ?, ?, ?, default)";
        String sqlUpdate = "update produto set nome = ?, marca = ?, codigo_barras = ?,unidade_medida=?, categoria_id = ?" + "where id = ?";

        PreparedStatement ps;
        if (entity.getId() == null) {
            ps = FabricadeConexao.getConexao().prepareStatement(sqlInsert);
        } else {
            ps = FabricadeConexao.getConexao().prepareStatement(sqlUpdate);
            ps.setLong(6, entity.getId());
        }

        ps.setString(1, entity.getNome());
        ps.setString(2, entity.getMarca());
        ps.setString(3, entity.getCodigoBarras());
        ps.setString(4, entity.getUnidadeMedida());
        ps.setLong(5, entity.getCategoria().getId());
        FabricadeConexao.getConexao().setAutoCommit(false);
        ps.execute();
        FabricadeConexao.getConexao().commit();
        FabricadeConexao.fecharConexao();
    }

    public void deletar(Produto entity) throws SQLException {
        String sqlDelete = "delete from produto where id = ?";
        PreparedStatement ps = FabricadeConexao.getConexao().prepareStatement(sqlDelete);
        ps.setLong(1, entity.getId());
        ps.execute();
        FabricadeConexao.fecharConexao();
    }

    public List<Produto> listar() throws SQLException {
        String sqlQuery = "select p.id as id_produto, p.nome as nome_produto,"
                + " p.marca, p.codigo_barras, p.unidade_medida, "
                + " c.id as id_categoria, c.nome as nome_categoria"
                + " from produto p inner join categoria c on c.id = p.categoria_id";
        PreparedStatement ps = FabricadeConexao.getConexao().prepareStatement(sqlQuery);
        //java.sql.ResultSet
        ResultSet rs = ps.executeQuery();
        List<Produto> produtos = new ArrayList<>();
        while (rs.next()) {
            Produto produto = new Produto();
            produto.setId(rs.getInt("id_produto"));
            produto.setNome(rs.getString("nome_produto"));
            produto.setMarca(rs.getString("marca"));
            produto.setCodigoBarras(rs.getString("codigo_barras"));
            produto.setUnidadeMedida(rs.getString("unidade_medida"));

            Categoria categoria = new Categoria();
            categoria.setId(rs.getInt("id_categoria"));
            categoria.setNome(rs.getString("nome_categoria"));

            produto.setCategoria(categoria);
            produtos.add(produto);
        }
        FabricadeConexao.fecharConexao();
        return produtos;
    }

}

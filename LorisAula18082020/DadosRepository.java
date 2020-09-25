package LorisAula18082020;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DadosRepository {

	private ConnectionManager connectionManager;

	public DadosRepository(ConnectionManager connectionManager) {
		this.connectionManager = connectionManager;
		this.createTable();		
	}
		

	private void createTable() {
		PreparedStatement psCreateTable = null;
		try {
			psCreateTable =  connectionManager.prepareStatement("create table if not exists Dados ("
					+ "id long not null primary key,"
					+ "titulo varchar(255) not null,"
					+ "numeros number(9,2) not null,"
					+ "descricao varchar(255) not null "
					+ ")");
			psCreateTable.executeLargeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psCreateTable != null) {
				try {
					psCreateTable.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}		
	}



	public Dados findById(Long id) throws SQLException {
		Dados found = null;
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, titulo, numeros, descricao from Dados where id = ?");
		psSelect.setLong(1, id);
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				found = new Dados(
						rsSelect.getLong("id"), 
						rsSelect.getString("titulo"), 
						rsSelect.getDouble("numeros"),
						rsSelect.getString("descricao")); 
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return found;
	}

	public Dados save(Dados Dados) {
		boolean exists = Dados.getId() != null;
		PreparedStatement psSave = null;
		try {
			if (exists) {
				psSave = connectionManager.prepareStatement("update Dados set Titulo = ?, numero = ?, descricao = ? where id = ?");
				psSave.setString(1, Dados.getTitulo());
				psSave.setDouble(2, Dados.getNumeros());
				psSave.setString(1, Dados.getDescricao());
				psSave.setLong(3, Dados.getId());
			} else {
				Long id = selectNewId();
				System.out.println("Novo id: " + id);
				psSave = connectionManager.prepareStatement("insert into Dados (id, titulo, numeros, descricao) values (?,?,?,?)");
				psSave.setLong(1, id);
				psSave.setString(2, Dados.getTitulo());
				psSave.setDouble(3, Dados.getNumeros());
				psSave.setString(4, Dados.getDescricao());
				Dados = new Dados(id,  Dados.getTitulo(), Dados.getNumeros());
			}
			psSave.executeUpdate();			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				psSave.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return Dados;
	}

	private Long selectNewId() throws SQLException {
		PreparedStatement psSelect = connectionManager.prepareStatement("select coalesce(max(id),0)+1 as newId from Dados");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			if (rsSelect.next()) {
				return rsSelect.getLong("newId");
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return null;
	}


	public void deleteById(Long id) throws SQLException {
		PreparedStatement psDelete = connectionManager.prepareStatement("delete from Dados where id = ?");
		psDelete.setLong(1, id);
		try {
			psDelete.executeUpdate();
		} finally {
			psDelete.close();
		}		
	}


	public List<Dados> findAll() throws SQLException {
		List<Dados> all = new ArrayList<>();
		PreparedStatement psSelect = connectionManager.prepareStatement("select id, titulo, numeros, descricao tipo from Dados");
		ResultSet rsSelect = psSelect.executeQuery();
		try {
			while (rsSelect.next()) {
				Dados Dados = new Dados(
						rsSelect.getLong("id"), 
						rsSelect.getString("titulo"),
						rsSelect.getDouble("numeros"),
						rsSelect.getString("descricao"));
				all.add(Dados);
			}
		} finally {
			rsSelect.close();
			psSelect.close();
		}
		return all;
	}

}	

package LorisAula18082020;

import java.sql.SQLException;
import java.util.List;

public class AppRepository {

	public static void main(String[] args) throws SQLException {
		ConnectionManager connManager = new ConnectionManager();
		try {
			DadosRepository repo = new DadosRepository(connManager);
			
			Dados FiltroEcoVerde934 = new Dados( null, "Filtro eco verde modelo 934", 1.2 ," asdasdasd ");
			FiltroEcoVerde934 = repo.save(FiltroEcoVerde934);
			connManager.commit();
			
			Long id = FiltroEcoVerde934.getId();
			System.out.println("Procurando pelo id: " + id);

			
			Dados encontradoPeloId = repo.findById(id);
			System.out.println(encontradoPeloId.toString());
			
			repo.deleteById(id);
			System.out.println("Recuperado após exclusão: " + repo.findById(id));
			
			List<Dados> DadosInseridos = repo.findAll();
			System.out.println("Teste de apresentação de todos os dados");
			for (Dados dados : DadosInseridos ) {
				System.out.println(dados.toString());
			}

			
		} catch (Exception e) {
			e.printStackTrace();			
		} finally {
			connManager.close();
		}
	}

}

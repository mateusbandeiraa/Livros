package persistence;

import entity.TicketSenha;

public class TicketDao extends Dao<TicketSenha>{

	public TicketDao() {
		super(new TicketSenha());
	}
	
	public void create(TicketSenha c){
		// Tenta remover um ticket anterior
		try{
			TicketSenha ts = this.findByUser(c.getUsuario().getId());
			this.delete(ts);
			System.out.println("Apagou");
		} catch (Exception ex) {
			System.out.println("Não apagou");
		}
		super.create(c);
		
	}
	
	public TicketSenha findByPass(String pass){
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from TicketSenha where ticketPass like ?");
		query.setString(0, pass);
		TicketSenha ts = (TicketSenha) query.uniqueResult();
		session.close();
		return ts;
	}
	
	public TicketSenha findByUser(Integer userID){
		session = HibernateUtil.getSessionFactory().openSession();
		query = session.createQuery("from TicketSenha where usuario_id = :id");
		query.setParameter("id", userID);
		TicketSenha ts = (TicketSenha) query.uniqueResult();
		session.close();
		return ts;
	}

}

package ProjetoIntegrador21;

import conectar.conectar;
import conectarMongo.conectarMongo;
import java.sql.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebControler {
    
    @RequestMapping("/pedido")
    public String Pedido(Model modelo) {
        System.out.println("Pedido");
        modelo.addAttribute("mensagem", "Pedido");
        return "pedido";
    }

    @RequestMapping(value = "/respostaPedido", method = RequestMethod.POST)
    public String RespPedido(Model modelo, String nome, String cpf, String cel, String end, String email, String senha, String donut, String bebida, String desc, float valor) {
        System.out.println("Resposta do pedido");
        modelo.addAttribute("mensagem1", "Olá, " + nome);
        conectar obj = new conectar();
        Connection conexao = obj.connectionMySql();
        obj.InsereCliente(conexao, nome, cpf, cel, end, email, senha);
        obj.closeConnectionMySql(conexao);

        conectarMongo con = new conectarMongo();
        con.insertValues(nome, cel, donut, bebida, desc, valor);
        con.getValues();
        return "respostaPedido";
    }

    @RequestMapping("/login")
    public String login(Model modelo) {
        System.out.println("login");
        return "login";
    }

    @RequestMapping(value = "/respostaLogin", method = RequestMethod.POST)
    public String respPedido(Model modelo, String email, String senha) {
        conectar obj = new conectar();
        Connection conexao = obj.connectionMySql();

        boolean x = obj.logar(conexao, email, senha);

        if (x == true) {
            modelo.addAttribute("mensagem2", "Ola " + email + ", como você está?");
        } else {
            modelo.addAttribute("mensagem2", "Usuário ou senha incorretos.");
        }
        obj.closeConnectionMySql(conexao);
        return "respostaLogin";
    }
    
}

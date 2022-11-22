package springBancoSQL;

import conectar.conectar;
import conectarMongo.conectarMongo;
import java.sql.Connection;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class WebController {

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

    @RequestMapping(value = "/fazerCadastro", method = RequestMethod.GET)
    public String FazerCadastro(Model modelo, String nome, String email, String endereco, String senha1, String senha2) {
        System.out.println("Fazer Cadastro");
        conectar obj = new conectar();
        Connection conexao = obj.connectionMySql();

        String senha = "";

        if (senha1.equals(senha2)) {
            senha = senha1;
            obj.InsereClientes(conexao, nome, email, endereco, senha);
            obj.closeConnectionMySql(conexao);
            return "login";
        } else {
            
            return "cadastro";
        }

    }

    @RequestMapping("/login")
    public String login(Model modelo) {
        System.out.println("login");
        return "login";
    }

    @RequestMapping(value = "/respostaLogin", method = RequestMethod.POST)
    public String respLogin(Model modelo, String email, String senha) {
        conectar obj = new conectar();
        Connection conexao = obj.connectionMySql();

        boolean x = obj.logar(conexao, email, senha);

        
        if (x == true) {
            modelo.addAttribute("mensagem3", conectar.pegarNome(conexao, email));
        } else {
        }
        obj.closeConnectionMySql(conexao);
        return "index";
    }
}

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

    @RequestMapping(value = "/meus-pedidos", method = RequestMethod.POST)
    public String RespPedido(Model modelo, String nome, String numero, String donut, String bebida, String desc, String endereco, float valor) {
        System.out.println("Resposta do pedido");
        modelo.addAttribute("mensagem1", "Oi");
        conectarMongo conm = new conectarMongo();
        conm.insertValues(nome, numero, donut, bebida, desc, endereco, valor);
        conm.getValues();
        return "meus-pedidos";
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
            modelo.addAttribute("mensagem4", "As senhas não coincidem");
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
        Connection con = obj.connectionMySql();

        boolean x = obj.logar(con, email, senha);
        
        if (x == false) {
            modelo.addAttribute("mensagem31", "Uusário ou senha incorretos");
            return "login";
        } else {
            modelo.addAttribute("mensagem3", "Bem-vindo(a) " + conectar.pegarNome(con, email));
        }
        
        obj.closeConnectionMySql(con);
        return "index";
    }
}

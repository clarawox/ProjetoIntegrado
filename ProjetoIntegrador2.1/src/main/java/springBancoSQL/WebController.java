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

    /*@RequestMapping("/pedido")
    public String Pedido(Model modelo) {
        System.out.println("Pedido");
        modelo.addAttribute("mensagem", "Pedido");
        return "pedido";
    }
    
    @RequestMapping("/contato")
    public String siteContato(Model modelo) {
        System.out.println("contato ajuda nois");
        return "contato";
    }
    
    @RequestMapping("/cadastro")
    public String siteCadastro(Model modelo) {
        System.out.println("cadastro ajuda nois");
        return "cadastro";
    }
    
    @RequestMapping("/login")
    public String siteLogin(Model modelo) {
        System.out.println("cadastro ajuda nois");
        return "login";
    }
    
    @RequestMapping("/meus-pedidos")
    public String siteMeusPedidos(Model modelo) {
        System.out.println("meus pedidos ajuda nois");
        return "meus-pedidos";
    }
    
    @RequestMapping("/sobre")
    public String siteSobre(Model modelo) {
        System.out.println("sobre ajuda nois");
        return "sobre";
    }*/
    @RequestMapping("/pedido")
    public String sitePedido(Model modelo) {
        System.out.println("pedido ajuda nois");
        return "pedido";
    }

    @RequestMapping(value = "/pedidos", method = RequestMethod.GET)
    public String RespPedido(Model modelo, String Nome, String Numero, String Donut, String Bebida, String Descricao, String Endereco, String Valor) {
        System.out.println("Resposta do pedido");
        //modelo.addAttribute("mensagem1", "Oi");
        conectarMongo conm = new conectarMongo();
        System.out.println(Nome + Numero + Donut + Bebida + Descricao + Endereco + Valor);
        conm.insertValues(Nome, Numero, Donut, Bebida, Descricao, Endereco, Valor);
        //conm.getValues();
        return "pedido";
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
            //return "login";
        } else {
            modelo.addAttribute("mensagem4", "As senhas não coincidem");
            return "cadastro";
        }
        return "login";
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
        System.out.println("teste login");
        boolean x = obj.logar(con, email, senha);

        if (x) {
            modelo.addAttribute("mensagem3", "Bem-vindo(a) " + conectar.pegarNome(con, email));
            obj.closeConnectionMySql(con);
            System.out.println("login2");
            return "index";
        } else {
            modelo.addAttribute("mensagem31", "Usuário ou senha incorretos");
            obj.closeConnectionMySql(con);
            return "login";
        }
    }
}

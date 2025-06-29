package br.com.gerenciamento.controller;

import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.service.ServiceUsuario;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioControllerTest {

    MockMvc mockMvc;

    @Autowired
    private UsuarioController usuarioController;

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.usuarioController).build();
    }

    @Test
    public void carregarPaginaIndex() throws Exception {
        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setId(1L);
        usuarioLogado.setUser("testuser");

        mockMvc.perform(get("/index")
                        .sessionAttr("usuarioLogado", usuarioLogado))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(model().attributeExists("aluno"));
    }

    @Test
    public void formularioDeCadastro() throws Exception {
        mockMvc.perform(get("/cadastro"))
                .andExpect(status().isOk())
                .andExpect(view().name("login/cadastro"))
                .andExpect(model().attributeExists("usuario"))
                 .andExpect(model().attribute("usuario", org.hamcrest.Matchers.isA(Usuario.class)));
    }

    @Test
    public void loginUsuario() throws Exception {
         Usuario usuarioParaLogin = new Usuario();
        usuarioParaLogin.setUser("loginTeste");
        usuarioParaLogin.setSenha("senha123");
        usuarioParaLogin.setEmail("login@teste.com");
        serviceUsuario.salvarUsuario(usuarioParaLogin);

        mockMvc.perform(post("/login")
                        .param("user", "loginTeste")
                        .param("senha", "senha123"))
                .andExpect(status().isOk())
                .andExpect(view().name("home/index"))
                .andExpect(request().sessionAttribute("usuarioLogado", notNullValue()));
    }

    @Test
    public void logoutUsuario() throws Exception {
        MockHttpSession session = new MockHttpSession();
        Usuario usuarioLogado = new Usuario();
        usuarioLogado.setId(1L);
        usuarioLogado.setUser("usuario");
        session.setAttribute("usuarioLogado", usuarioLogado);

        mockMvc.perform(post("/logout").session(session))
                .andExpect(status().isOk())
                .andExpect(view().name("login/login"))
                .andExpect(request().sessionAttributeDoesNotExist("usuarioLogado"));
    }
}

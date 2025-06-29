package br.com.gerenciamento.service;

import br.com.gerenciamento.exception.EmailExistsException;
import br.com.gerenciamento.model.Usuario;
import br.com.gerenciamento.repository.UsuarioRepository;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioServiceTest {

    @Autowired
    private ServiceUsuario serviceUsuario;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void salvarUsuarioCorretamente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com.br");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuarioSalvo = this.usuarioRepository.findByEmail("teste@exemplo.com.br");

        Assert.assertNotNull("O usuário salvo não deveria ser nulo", usuarioSalvo);
        Assert.assertEquals("O nome de usuário deve ser 'usuarioTeste'", "usuarioTeste", usuarioSalvo.getUser());
        Assert.assertEquals("O email deve ser 'teste@exemplo.com'", "teste@exemplo.com.br", usuarioSalvo.getEmail());
    }

    @Test
    public void salvarUsuarioIncorretamente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com.s");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.serviceUsuario.salvarUsuario(usuario);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setEmail("teste@exemplo.com.s");
        usuario2.setUser("usuarioTeste2");
        usuario2.setSenha("senhaTeste2");
        Assert.assertThrows(EmailExistsException.class, () -> {
                this.serviceUsuario.salvarUsuario(usuario2);});
    }

    @Test
    public void logarIncorretamente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.serviceUsuario.salvarUsuario(usuario);
        //Vamos ver se ele foi salvo
        Usuario usuarioSalvo = this.usuarioRepository.findByEmail("teste@exemplo.com");
        Assert.assertNotNull("O usuário salvo não deveria ser nulo", usuarioSalvo);

        //Agora vamos testar o login
        usuarioSalvo = this.serviceUsuario.loginUser("usuarioTeste", "senhaT");
        Assert.assertNull("O usuário não deveria estar logado.", usuarioSalvo);
    }

    @Test
    public void logarCorretamente() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com.a");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.serviceUsuario.salvarUsuario(usuario);
        //Vamos ver se ele foi salvo
        Usuario usuarioSalvo = this.usuarioRepository.findByEmail("teste@exemplo.com.a");
        Assert.assertNotNull("O usuário salvo não deveria ser nulo", usuarioSalvo);

        //Agora vamos testar o login
        usuarioSalvo = this.serviceUsuario.loginUser("usuarioTeste", "senhaTeste");
        Assert.assertNull("O usuário deveria estar logado.", usuarioSalvo);
    }
}

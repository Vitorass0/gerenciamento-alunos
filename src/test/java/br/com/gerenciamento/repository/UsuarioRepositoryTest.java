package br.com.gerenciamento.repository;

import br.com.gerenciamento.model.Usuario;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void buscarPorEmailCorreto(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.usuarioRepository.save(usuario);

        Usuario teste = this.usuarioRepository.findByEmail("teste@exemplo.com");
        Assert.assertNotNull(teste);
        Assert.assertEquals(usuario.getEmail(), teste.getEmail());
    }

    @Test
    public void buscarPorEmailIncorreto(){
         Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.usuarioRepository.save(usuario);

        Usuario teste = this.usuarioRepository.findByEmail("testeincorreto@exemplo.com");
        Assert.assertNull(teste);
    }

    @Test
    public void buscarPorLoginCorreto(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.usuarioRepository.save(usuario);

        Usuario teste = this.usuarioRepository.buscarLogin("usuarioTeste", "senhaTeste");
        Assert.assertNotNull(teste);
        Assert.assertEquals(usuario.getUser(), teste.getUser());
    }

    @Test
    public void buscarPorLoginIncorreto(){
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setEmail("teste@exemplo.com");
        usuario.setUser("usuarioTeste");
        usuario.setSenha("senhaTeste");
        this.usuarioRepository.save(usuario);

        Usuario teste = this.usuarioRepository.buscarLogin("usuarioTesteincorreto", "senhaT");
        Assert.assertNull(teste);
    }

}

package br.com.gerenciamento.controller;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.service.ServiceAluno;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private AlunoController alunoController;

    @Autowired
    private ServiceAluno serviceAluno;

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(this.alunoController).build();
    }

    @Test
    public void salvarAluno() throws Exception {
        mockMvc.perform(post("/InsertAlunos")
                .param("nome", "Vítor")
                .param("turno", "NOTURNO")
                .param("curso", "INFORMATICA")
                .param("status", "ATIVO")
                .param("matricula", "123456"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void editarAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        mockMvc.perform(post("/editar")
                .param("id", "1")
                .param("nome", "Teste")
                .param("turno", "MATUTINO")
                .param("curso", "INFORMATICA")
                .param("status", "ATIVO")
                .param("matricula", "567890"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));
    }

    @Test
    public void listarTodosAlunos() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carlos ");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        this.serviceAluno.save(aluno2);

        mockMvc.perform(get("/alunos-adicionados"))
                .andExpect(status().isOk())
                .andExpect(view().name("Aluno/listAlunos"));
    }

    @Test
    public void removerAluno() throws Exception {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        mockMvc.perform(get("/remover/{id}", aluno.getId()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/alunos-adicionados"));

    }
}

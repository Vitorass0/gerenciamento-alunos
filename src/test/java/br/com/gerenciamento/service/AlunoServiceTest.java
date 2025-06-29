package br.com.gerenciamento.service;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import br.com.gerenciamento.repository.AlunoRepository;
import jakarta.validation.ConstraintViolationException;
import org.junit.*;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoServiceTest {

    @Autowired
    private ServiceAluno serviceAluno;
    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void getById() {
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vinicius");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno alunoRetorno = this.serviceAluno.getById(1L);
        Assert.assertTrue(alunoRetorno.getNome().equals("Vinicius"));
    }

    @Test
    public void salvarSemNome() {
        Aluno aluno = new Aluno();
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.ADMINISTRACAO);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        Assert.assertThrows(ConstraintViolationException.class, () -> {
                this.serviceAluno.save(aluno);});
    }

    @Test
    public void buscarPorNome() {
        Aluno aluno = new Aluno();
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setNome("Josafa ");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.ADMINISTRACAO);
        aluno2.setStatus(Status.ATIVO);
        aluno2.setMatricula("654321");
        this.serviceAluno.save(aluno2);


        List<Aluno> alunoRetornado = this.serviceAluno.findByNomeContainingIgnoreCase("josafa");
        Assert.assertEquals(1, alunoRetornado.size());
    }

    @Test
    public void buscarPorStatusAtivo() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Rodrigo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("aaaa");
        this.serviceAluno.save(aluno);

        List<Aluno> alunoRetornado = this.serviceAluno.findByStatusAtivo();
        Assert.assertTrue(alunoRetornado.get(0).getNome().equals("Rodrigo"));
    }

     @Test
    public void buscarPorStatusInativo() {

        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Rodrigo");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("aaaa");
        this.serviceAluno.save(aluno);

        List<Aluno> alunoRetornado = this.serviceAluno.findByStatusInativo();
        Assert.assertTrue(alunoRetornado.get(0).getNome().equals("Rodrigo"));
    }


    @Test
    public void listarTodos(){
        Aluno aluno = new Aluno();
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.INATIVO);
        aluno.setMatricula("123456");
        this.serviceAluno.save(aluno);

        Aluno aluno2 = new Aluno();

        aluno2.setNome("Carlos ");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        this.serviceAluno.save(aluno2);

        Aluno aluno3 = new Aluno();

        aluno3.setNome("Vinicius");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("587654");
        this.serviceAluno.save(aluno3);

        Aluno aluno4 = new Aluno();

        aluno4.setNome("Claudia");
        aluno4.setTurno(Turno.MATUTINO);
        aluno4.setCurso(Curso.ENFERMAGEM);
        aluno4.setStatus(Status.ATIVO);
        aluno4.setMatricula("987654");
        this.serviceAluno.save(aluno4);

        List<Aluno> listaAlunos = this.serviceAluno.findAll();
        Assert.assertEquals(4, listaAlunos.size());
    }
}
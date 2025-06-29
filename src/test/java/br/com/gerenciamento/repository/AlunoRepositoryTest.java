package br.com.gerenciamento.repository;

import br.com.gerenciamento.enums.Curso;
import br.com.gerenciamento.enums.Status;
import br.com.gerenciamento.enums.Turno;
import br.com.gerenciamento.model.Aluno;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AlunoRepositoryTest {

    @Autowired
    private AlunoRepository alunoRepository;

    @Test
    public void encontrarPorStatusAtivo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carlos ");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        this.alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Vinicius");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("123456");
        this.alunoRepository.save(aluno3);

        List<Aluno> alunosStatusAtivo = alunoRepository.findByStatusAtivo();
        Assert.assertEquals(2, alunosStatusAtivo.size());
    }

    @Test
    public void encontrarPorStatusInativo(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("Carlos ");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        this.alunoRepository.save(aluno2);

        Aluno aluno3 = new Aluno();
        aluno3.setId(3L);
        aluno3.setNome("Vinicius");
        aluno3.setTurno(Turno.NOTURNO);
        aluno3.setCurso(Curso.ADMINISTRACAO);
        aluno3.setStatus(Status.ATIVO);
        aluno3.setMatricula("123456");
        this.alunoRepository.save(aluno3);

         List<Aluno> alunosStatusInativo = alunoRepository.findByStatusInativo();
        Assert.assertEquals(1, alunosStatusInativo.size());
    }

    @Test
    public void encontrarPorNome(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        Aluno aluno2 = new Aluno();
        aluno2.setId(2L);
        aluno2.setNome("VÍTor");
        aluno2.setTurno(Turno.MATUTINO);
        aluno2.setCurso(Curso.BIOMEDICINA);
        aluno2.setStatus(Status.INATIVO);
        aluno2.setMatricula("654321");
        this.alunoRepository.save(aluno2);

        List<Aluno> alunosNome = alunoRepository.findByNomeContainingIgnoreCase("vítor");
        Assert.assertEquals(2, alunosNome.size());
    }

    @Test
    public void salvarAluno(){
        Aluno aluno = new Aluno();
        aluno.setId(1L);
        aluno.setNome("Vítor");
        aluno.setTurno(Turno.NOTURNO);
        aluno.setCurso(Curso.INFORMATICA);
        aluno.setStatus(Status.ATIVO);
        aluno.setMatricula("123456");
        this.alunoRepository.save(aluno);

        Assert.assertNotNull("O aluno não deveria ser nulo", aluno);
        Assert.assertEquals("O nome do aluno deveria ser 'Vítor'", "Vítor", aluno.getNome());
        Assert.assertEquals("O turno do aluno deveria ser 'NOTURNO'", Turno.NOTURNO, aluno.getTurno());
        Assert.assertEquals("O curso do aluno deveria ser 'INFORMATICA'", Curso.INFORMATICA, aluno.getCurso());
        Assert.assertEquals("O status do aluno deveria ser 'ATIVO'", Status.ATIVO, aluno.getStatus());
        Assert.assertEquals("A matrícula do aluno deveria ser '123456'", "123456", aluno.getMatricula());
    }

}

package br.com.fiap.tc.batch.produtos.processador.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class CargaConfiguration {

	private final PlatformTransactionManager transactionManager;

    @Value("${carga.input-path}")
    private String diretorio;


	@Bean
    public Job job(
    		@Qualifier("passoInicial") Step passoInicial,
    		@Qualifier("moverArquivosStep") Step moverArquivosStep,
    		JobRepository jobRepository) {
        return new JobBuilder("importar-arquivos", jobRepository)
                .start(passoInicial)
                .next(moverArquivosStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step passoInicial(
    		@Qualifier("reader") ItemReader<Produto> reader,
    		@Qualifier("writer") ItemWriter<Produto> writer,
    		@Qualifier("processor") ProdutoProcessor processor,
    		JobRepository jobRepository) {
        return new StepBuilder("passo-inicial", jobRepository)
                .<Produto, Produto>chunk(200, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<Produto> reader() {
        return new FlatFileItemReaderBuilder<Produto>()
                .name("leitura-csv")
                .resource(new FileSystemResource( diretorio + "/dados.csv"))
                .comments("--")
                .delimited()
                .delimiter(";")
                .names("descricao", "preco", "quantidade")
                .fieldSetMapper(new ProdutoMapper())
                .build();
    }

    @Bean
    public ItemWriter<Produto> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Produto>()
                .dataSource(dataSource)
                .sql(
                  "INSERT INTO produto (descricao, preco, quantidade) VALUES" +
                          " (:descricao, :preco, :quantidade)"

                )
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public ProdutoProcessor processor() {
        return new ProdutoProcessor();
    }

    @Bean
    public Tasklet moverArquivosTasklet() {
        return (contribution, chunkContext) -> {
            File pastaOrigem = new File(diretorio);
            File pastaDestino = new File(diretorio + "/processados");

            if (!pastaDestino.exists()) {
                pastaDestino.mkdirs();
            }

            File[] arquivos = pastaOrigem.listFiles((dir, name) -> name.endsWith(".csv"));

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    File arquivoDestino = new File(pastaDestino, arquivo.getName());
                    if (arquivo.renameTo(arquivoDestino)) {
                        log.info("Arquivo movido: {}", arquivo.getName());
                    } else {
                    	log.warn("Não foi possível mover o arquivo: {}", arquivo.getName());
                        throw new RuntimeException("Não foi possível mover o arquivo: " + arquivo.getName());//NOSONAR
                    }
                }
            }
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step moverArquivosStep(JobRepository jobRepository) {
        return new StepBuilder("mover-arquivo", jobRepository)
                .tasklet(moverArquivosTasklet(), transactionManager)
                .allowStartIfComplete(true)
                .build();
    }

}

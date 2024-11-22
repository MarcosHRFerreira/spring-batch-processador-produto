package br.com.fiap.tc.batch.produtos.processador.gateway.springbatch;


import br.com.fiap.tc.batch.produtos.processador.domain.Carga;
import br.com.fiap.tc.batch.produtos.processador.exception.ErroAoProcessarJobException;
import br.com.fiap.tc.batch.produtos.processador.gateway.JobGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SpringBatchGateway implements JobGateway {

	private JobLauncher jobLauncher;
	private Job job;

	@Autowired
	public SpringBatchGateway(
			JobLauncher jobLauncher,
			@Qualifier("job") Job job) {
		this.jobLauncher = jobLauncher;
		this.job = job;
	}


	@Override
	public void execute(Carga carga) {
		try {
    		JobParameters jobParameters = new JobParameters();
    		jobLauncher.run(job, jobParameters);

		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new ErroAoProcessarJobException();
		}

	}

}

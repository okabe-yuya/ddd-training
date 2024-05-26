import models_v2

class ApplyForJobRepositoryImpl: ApplyForJobRepository {
  private val applyForJobs = mutableMapOf<ApplyForJobId, ApplyForJob>()

  override fun findById(id: ApplyForJobId): ApplyForJob? {
    return applyForJobs[id]
  }

  override fun save(instance: ApplyForJob): ApplyForJobId {
    applyForJobs[instance.id] = instance
  }
}


class ApplyForJobUsecase(
  applyForJobRepository: ApplyForJobRepository
) {
  fun makeApplyForJob(
    applicantId: ApplicantId,
    companyId: CompanyId,
    applyerId: UserId
  ) {
    val applyForJob = ApplyForJob.create(
      applicantId = applicantId,
      companyId = companyId,
      applyerId = applyerId
    )
    applyForJobRepository.save(applyForJob)
  }

  fun scheduleAdjustment(applyForJobId: ApplyForJobId) {
    val applyForJob = applyForJobRepository.findById(applyForJobId)
    applyForJob.scheduleAdjustment()
    applyForJobRepository.save(applyForJob)
  }
}

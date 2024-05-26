class ApplyForJob(
  id: ApplyFroJobID,
  applicantId: ApplicantId,
  companyId: CompanyId,
  status: ApplyForJobStaus,
  progressStatus: ApplyForJobProgressStatus,
  applyerId: UserId,
  scheduleAt: LocalDateTime?,
  interviewAt: LocalDateTime?,
  statusChangedAt: LocalDateTime?,
) {
  val id: ApplyForJobId = id
  val applicantId: ApplicantId = applicantId
  val companyId: CompanyId = companyId
  var applyerId: UserId = applyerId
  var status: ApplyForJobStatus = status; private set
  var progressStatus: ApplyForJobProgressStatus = progressStatus; private set
  var scheduleAt: LocalDateTime? = scheduleAt; private set
  var interviewAt: LocalDateTime? = interviewAt; private set
  var statusChangeAt: LocalDateTime? = statusChangeAt; private set

  fun scheduleAdjustment() {
    check(this.progressStatus == ApplyForJobProgressStatus.APPLY_DONE)
    this.progressStatus = ApplyForJobProgressStatus.SCHEDULE_ADJUSTMENT
  }

  fun scheduleAdjustmentDone(scheduleAt: LocalDateTime) {
    check(this.progressStatus == ApplyForJobProgressStatus.SCHEDULE_ADJUSTMENT)
    this.progressStatus = ApplyForJobProgressStatus.SCHEDULE_ADJUSTMENT_DONE
    this.scheduleAt = scheduleAt
  }

  fun startMetting() {
    check(this.progressStatus == ApplyForJobProgressStatus.SCHEDULE_ADJUSTMENT_DONE)
    check(this.scheduleAt != null)
    check(this.scheduleAt >= LocalDateTime.now())

    this.progressStatus = ApplyForJobProgressStatus.IN_METTING
    this.interviewAt = LocalDateTime.now()
  }

  fun doneMetting() {
    check(this.progressStatus == ApplyForJobProgressStatus.IN_METTING)
    this.progressStatus = ApplyForJobProgressStatus.METTING_DONE
  }

  fun rateApplicant(status: ApplyJobStatus) {
    check(this.progressStatus == ApplyForJobProgressStatus.METTING_DONE)
    check(this.status == null)

    this.progressStatus = ApplyForJobProgressStatus.RATED
    this.status = status
    this.statusChangeAt = LocalDateTime.now()
  } 

  companion object {
    fun create(
      applicantId: ApplicantId,
      companyId: CompanyId,
      applyerId: UserId,
    ): ApplyForJob {
      return ApplyForJob(
        id = ApplyForJobId.next(),
        applicantId = applicantId,
        companyId = companyId, 
        status = null,
        progressStatus = ApplyForJobProgressStatus.APPLY_DONE,
        applyerId = applyerId,
        scheduleAt = null,
        interviewAt = null,
        statusChangedAt = null, 
      )    
    }
}

interface ApplyForJobRepository {
  fun findById(id: ApplyForJobId): ApplyForJob?
  fun save(instance: ApplyForJob): ApplyForJobId:
}

value class ApplyForJobId(val value: UUID) {
  companion object {
    fun next() = ApplyForJobId(UUID.randomUUID())
  }
}

value class ApplicantId(val value: UUID) {
  companion object {
    fun next() = ApplicantId(UUID.randomUUID())
  }
}

value class CompanyId(val value: UUID) {
  companion object {
    fun next() = CompanyId(UUID.randomUUID())
  }
}

value class UserId(val value: UUID) {
  companion object {
    fun next() = UserId(UUID.randomUUID())
  }
}

enum class ApplyForJobStatus {
  UNDATED, ADOPTION, REJECTION, REFUSAL
}

enum class ApplyForJobProgressStatus {
  APPLY_DONE, SCHEDULE_ADJUSTMENT, SCHEDULE_ADJUSTMENT_DONE, IN_METTING, METTING_DONE, RATED
}

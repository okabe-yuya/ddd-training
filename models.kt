/** 求人応募 **/

class ApplyForJob {
  var id: UUID? = null, // ID
  var applicantId: UUID? = null, // 応募者のID
  var companyId: UUID? = null, // 応募した企業のID
  var status: Int? = null, // ステータス
  var progressStatus: Int? = null, // 進捗ステータス
  var applyerId: UUID? = null, // 担当者のID
  var scheduleAt: Calendar? =  null, // 面接予定日
  var interviewAt: Calendar? = null, // 面接実施日
  var statusChangedAt: Calendar? = null, // 面接完了日
}

object ApplyForJobStatus {
  const val UNRATED: Int = 1,
  const val ADOPTION: Int = 2,
  const val REJECTION: Int = 3,
  const val REFUSAL: Int = 3,
}

object ApplyForJobProgressStatus {
  const val APPlY_DONE: Int = 1,
  const val SCHEDULE_ADJUSTMENT: Int = 2,
  const val SCHEDULE_ADJUSTMENT_DONE: Int = 3,
  const val IN_METTING: Int = 4,
  const val METTING_DONE: Int = 5,
  const val RATED: Int = 6,
}

/** usecase **/
fun makeApplyForJob(applicantId: UUID, companyId: UUID, applyerId: UUID): UUID {
  var applyForJob = ApplyForJob()
  applyForJob.id = UUID.randomUUID()
  applyForJob.applicantId = applicantId 
  applyForJol.companyId = companyId 
  applyForJob.status = ApplyForJobStatus.UNRATED
  applyForJob.progressStatus = ApplyForJobProgressStatus.APPLY_DONE
  applyForJob.applyerId = applyerID
  applyForJobRepository.save(applyForJob)

  return applyForJob.id!!
}

/** 面接予定日の調整を依頼する **/
fun scheduleAdjustmentApplyForJob(applyForJobId: UUID) {
  var applyForJob = applyForJobRepository.findById(applyForJobId)
  applyForJob.progressStatus = ApplyForJobProgressStatus.SCHEDULE_ADJUSTMENT
  applyForJobRepository.save(applyForJob)
}

/** 面接予定日日の確定をする **/
fun scheduleAdjustmentDoneApplyForJob(applyForJobId: UUID, interviewAt: Calendar) {
  var applyForJob = applyForJobRepository.findById(applyForJobID)
  applyForJob.progressStatus = ApplyForJobProgressStatus.SCHEDULE_ADJUSTMENT_DONE
  applyForJob.interviewAt = interviewAt
  applyForJobRepository.save(applyForJob)
}

/** 面接を開始する **/
fun startMettingApplyForJob(applyForJob: UUID) {
  var applyForJob = applyForJobRepository.findById(applyForJobID)
  applyForJob.interviewAt = Calendar.getInstance()
  applyForJob.progressStatus = ApplyForJobProgressStatus.IN_MEETING
  applyForJobRepository.save(applyForJob)
}

/** 面接完了 **/
fun doneMettingApplyForJob(applyForJob: UUID) {
  var applyForJob = applyForJobRepository.findById(applyForJobID)
  applyForJob.progressStatus = ApplyForJobProgressStatus.MEETING_DONE
  applyForJobRepository.save(applyForJob)
}

/** 応募者の評価 **/
fun rateApplicantApplyForJob(applyForJob: UUID, status: ApplyForJobStatus) {
  var applyForJob = applyForJobRepository.findById(applyForJobID)
  applyForJob.progressStatus = ApplyForJobProgressStatus.RATED
  applyForJob.status = status
  applyForJob.statusChangeAt = Calendar.getInstance()
  applyForJobRepository.save(applyForJob)
}


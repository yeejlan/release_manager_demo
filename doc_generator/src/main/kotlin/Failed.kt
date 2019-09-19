package doc_generator

@Retention(AnnotationRetention.SOURCE)
@Target(AnnotationTarget.FUNCTION)
annotation class Failed(
	val value: Array<String> = arrayOf()
)
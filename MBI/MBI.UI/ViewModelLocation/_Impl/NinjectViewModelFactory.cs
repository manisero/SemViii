using Ninject;

namespace MBI.UI.ViewModelLocation._Impl
{
	public class NinjectViewModelFactory : IViewModelFactory
	{
		private readonly IKernel _kernel;

		public NinjectViewModelFactory(IKernel kernel)
		{
			_kernel = kernel;
		}

		public TViewModel Create<TViewModel>()
		{
			return _kernel.Get<TViewModel>();
		}
	}
}

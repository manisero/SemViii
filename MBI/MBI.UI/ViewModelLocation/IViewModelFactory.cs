namespace MBI.UI.ViewModelLocation
{
	public interface IViewModelFactory
	{
		TViewModel Create<TViewModel>();
	}
}
